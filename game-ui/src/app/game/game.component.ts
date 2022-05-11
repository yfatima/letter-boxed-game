import { ThrowStmt } from '@angular/compiler';
import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { AbstractControl, FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { ActivatedRoute, Router } from '@angular/router';
import Chart from 'chart.js/auto';
import { interval, Subscription, timer } from 'rxjs';
import { Game } from 'src/models/game.model';
import { Player } from 'src/models/player.model';
import { GameService } from 'src/services/game.service';
import { MoveService } from 'src/services/move.service';
import { PlayerService } from 'src/services/player.service';
import { sha256, sha224 } from 'js-sha256';

//creates the outer box with letters from the game letters attribute
function drawOutterBox(data: string[]) {
  var canvas = <HTMLCanvasElement>document.getElementById('letteredbox');
  var ctx = canvas.getContext('2d');
  //left side
  ctx.fillText(data[0], 10, 35);
  ctx.fillText(data[2], 10, 125);
  ctx.fillText(data[1], 10, 80);
  //top
  ctx.fillText(data[3], 85, 15);
  ctx.fillText(data[4], 145, 15);
  ctx.fillText(data[5], 210, 15);
  //right side
  ctx.fillText(data[6], 285, 35);
  ctx.fillText(data[7], 285, 80);
  ctx.fillText(data[8], 285, 125);
  //bottom
  ctx.fillText(data[9], 85, 145);
  ctx.fillText(data[10], 145, 145);
  ctx.fillText(data[11], 210, 145);
}

//static coordinates of each letter around the box
const boxcoords: [number, number][] = [
  [0, 15],
  [0, 80],
  [0, 130],
  [60, 0],
  [150, 0],
  [230, 0],
  [300, 15],
  [300, 80],
  [300, 130],
  [60, 150],
  [80, 150],
  [230, 150]
];


@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss']
})
export class GameComponent implements OnInit {

  //the words must be only letters and 5 characters long
  wordFormControl = new FormControl('', [Validators.required, Validators.pattern(
    '^[A-Za-z]{5}$')]);

  //local variables
  game: Game;
  player: Player;
  intervelcounter: any;
  startcounter: any;
  score: number = 1;
  showbutton: boolean = false;
  timeoutcounter: number = 0;
  playerhashvalue: string;

  subscription: Subscription;

  constructor(

    private playerService: PlayerService,
    private gameService: GameService,
    private moveService: MoveService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {

    this.showbutton = true;
    this.wordFormControl.disable();
    let action = this.route.snapshot.paramMap.get('action');
    let username = sessionStorage.getItem("username");
    this.playerhashvalue = sha256(username);

    //if this statement is true then this player is player 1 in the game 
    if (username != null && action == 'newgame') {
      console.log("in new game");
      //getting the player with the given username
      this.playerService.getPlayerByUsername(username, this.playerhashvalue).subscribe(data => {
        //console.log(data);
        this.player = data;
        //make a game 
        this.gameService.createGame(this.player).subscribe(data => {
          this.game = data;
          //console.log(this.game);
          drawOutterBox(this.game.letters);
        });
      });

      //counter to check if player 2 joined or not yet
      this.startcounter = setInterval(() => {
        this.gameService.getGameStatus(this.game.id).subscribe((data) => {
          if (data.gameStatus != "active") {
            this.game = data;
            this.destoryStartInterval();
          }
        });
      }, 10000);

      //counter to check if its my turn or not
      this.intervelcounter = setInterval(() => {
        console.log('testing intervel player 1');
        //console.log(this.player);
        //check if the player is taking too long or not
        if (this.timeoutcounter != 10) {
          console.log(this.timeoutcounter);
          this.gameService.getGameStatus(this.game.id).subscribe(data => {
            this.game = data;
            if (this.game.gameStatus == "finish") {
              //console.log("generating finish alert");
              this.destoryIntervel(this.game.p2Id);
            }
            if (this.game.gameStatus == this.player.userName) {
              this.showbutton = false;
              this.wordFormControl.enable();
            } else {
              this.showbutton = true;
              this.wordFormControl.disable();
            }
            this.timeoutcounter++;
          });
        } else {
          if (this.game.gameStatus == this.player.userName) {
            this.skipMove();
          }
          this.timeoutcounter = 0;
        }

      }, 10000);
    //if this is true than this player is player 2 in the game
    } else if (username != null && action != "newgame") {

      //get the player from the backend
      this.playerService.getPlayerByUsername(username, this.playerhashvalue).subscribe(data => {
        this.player = data;
        //join the game 
        this.gameService.joinGame(action, this.player).subscribe(data => {
          this.game = data;
          if (this.game.id != null) {
            drawOutterBox(this.game.letters);
          } else {
            this.router.navigate(['/home']);
          }
          //at everytime intervel 
          //check if it is my turn
          //enable move check button else disable and update game
          //if my score is 2 then stop intervel, show win game alert
          this.intervelcounter = setInterval(() => {
            console.log('testing intervel player 2');
            //console.log(this.player);
            if (this.timeoutcounter != 10) {
              //console.log(this.timeoutcounter);
              this.gameService.getGameStatus(this.game.id).subscribe(data => {
                this.game = data;
                if (this.game.gameStatus == "finish") {
                  //console.log("generating finish alert");
                  this.destoryIntervel(this.game.p1Id);
                }
                if (this.game.gameStatus == this.player.userName) {
                  this.showbutton = false;
                  this.wordFormControl.enable();
                } else {
                  this.showbutton = true;
                  this.wordFormControl.disable();
                }
                this.timeoutcounter++;
              });
            } else {
              if (this.game.gameStatus == this.player.userName) {
                this.skipMove();
              }
              this.timeoutcounter = 0;
            }
          }, 10000);

        });

      });


    }
  }

  //this functions makes a request to check if the word is valid and follows all the rules given by the player 
  checkWord() {
    console.log(this.wordFormControl.value);
    //make move
    if (!this.wordFormControl.hasError('pattern') && this.game.p2Id != "none") {
      this.timeoutcounter = 0;
      this.moveService.createMove(this.player, this.wordFormControl.value).subscribe(data => {
        //console.log(data);
        if (data.id == null) {
          alert("word used before or not a valid English word");
        } else {
          //draw lines on the box for this word
          this.drawLines(this.wordFormControl.value);
          this.game = data;
          //console.log("end game");
          //check if the player won after this move
          this.playerService.getPlayerScore(this.player.userName, this.playerhashvalue).subscribe(data2 => {
            if (data2 == this.game.winScore && data2 != -1) {
              this.destoryIntervel(this.player.userName);
            }
          });
        }

      });
    } else {
      alert("please enter a word that matches the pattern");
    }
  }

  //this function skips the player's turn
  skipMove() {
    this.moveService.skipMove(this.player).subscribe(data => {
      //console.log(data);
      this.game = data;
    });
  }

  //this function draws the 4 lines for a word with 5 letters
  drawLines(word: string) {
    const usingSplit = word.split('');
    var char1Index = this.game.letters.indexOf(usingSplit[0].toUpperCase());
    var char2Index = this.game.letters.indexOf(usingSplit[1].toUpperCase());
    var char3Index = this.game.letters.indexOf(usingSplit[2].toUpperCase());
    var char4Index = this.game.letters.indexOf(usingSplit[3].toUpperCase());
    var char5Index = this.game.letters.indexOf(usingSplit[4].toUpperCase());

    var canvas = <HTMLCanvasElement>document.getElementById('tutorial');
    var ctx = canvas.getContext('2d');

    ctx.strokeStyle = 'purple';
    ctx.lineWidth = 3;

    // draw lines
    ctx.beginPath();
    ctx.moveTo(boxcoords[char1Index][0], boxcoords[char1Index][1]);
    ctx.lineTo(boxcoords[char2Index][0], boxcoords[char2Index][1]);
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(boxcoords[char2Index][0], boxcoords[char2Index][1]);
    ctx.lineTo(boxcoords[char3Index][0], boxcoords[char3Index][1]);
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(boxcoords[char3Index][0], boxcoords[char3Index][1]);
    ctx.lineTo(boxcoords[char4Index][0], boxcoords[char4Index][1]);
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(boxcoords[char4Index][0], boxcoords[char4Index][1]);
    ctx.lineTo(boxcoords[char5Index][0], boxcoords[char5Index][1]);
    ctx.stroke();

    //console.log(this.game.letters.indexOf(usingSplit[0].toUpperCase()));
  }

  //this function clear the lines on the board
  clearLines() {
    var canvas = <HTMLCanvasElement>document.getElementById('tutorial');
    var ctx = canvas.getContext('2d');
    ctx.clearRect(0, 0, canvas.width, canvas.height);
  }

  //this function destorys the intervel for starting the game when player 2 joins
  destoryStartInterval() {
    if (this.startcounter) {
      clearInterval(this.startcounter);
      console.log("start interval destoryed!");
    }
  }

  //this function destory the intervel of checking gamestatus after the game has ended
  destoryIntervel(username: string) {
    this.showbutton = true;
    this.wordFormControl.disable();
    if (this.intervelcounter) {
      clearInterval(this.intervelcounter);
      alert("player " + username + " won!!");
      //this.router.navigate(['home']);
    }
  }

  //this function destroys other 2 intervels when the user goes to other pages on the website
  ngOnDestroy() {
    clearInterval(this.startcounter);
    clearInterval(this.intervelcounter);
    console.log("destorying intervals");
  }

}





