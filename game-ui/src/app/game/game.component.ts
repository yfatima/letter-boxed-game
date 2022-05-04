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

  wordFormControl = new FormControl('', [Validators.required, Validators.pattern(
    '^[A-Za-z]{5}$')]);

  game: Game;
  player: Player;
  intervelcounter: any;
  startcounter: any;
  score: number = 1;
  showbutton: boolean = false;

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
    //get user by username
    if (username != null && action == 'newgame') {
      console.log("in new game");
      this.playerService.getPlayerByUsername(username).subscribe(data => {
        //console.log(data);
        this.player = data;
        //make a game 
        this.gameService.createGame(this.player).subscribe(data => {
          this.game = data;
          //console.log(this.game);
          drawOutterBox(this.game.letters);
        });
      });

      this.startcounter = setInterval(() => {
        this.gameService.getGameStatus(this.game.id).subscribe((data) => {
          if (data.gameStatus != "active") {
            this.game = data;
            this.destoryStartInterval();
          }
        });
      }, 10000);

      this.intervelcounter = setInterval(() => {
        console.log('testing intervel player 1');
        console.log(this.player);
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
        });
      }, 15000);

    } else if (username != null && action != "newgame") {

      this.playerService.getPlayerByUsername(username).subscribe(data => {
        //console.log(data);
        this.player = data;
        //make a game 
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
          //if my score is 3 then stop intervel, show win game alert
          this.intervelcounter = setInterval(() => {
            console.log('testing intervel player 2');
            console.log(this.player);
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
            });
          }, 20000);

        });

      });


    }
  }

  checkWord() {
    console.log(this.wordFormControl.value);
    this.drawLines(this.wordFormControl.value);
    //make move
    if (!this.wordFormControl.hasError('pattern') && this.game.p2Id != "none") {
      this.moveService.createMove(this.player, this.wordFormControl.value).subscribe(data => {
        //console.log(data);
        if (data.id == null) {
          alert("word used before or not a valid English word");
        } else {
          this.game = data;
          //console.log("end game");
          this.playerService.getPlayerScore(this.player.userName).subscribe(data2 => {
            if (data2 == this.game.winScore) {
              this.destoryIntervel(this.player.userName);
            }
          });
        }

      });
    } else {
      alert("please enter a word that matches the pattern");
    }
  }

  skipMove() {
    this.moveService.skipMove(this.player).subscribe(data => {
      console.log(data);
      this.game = data;
    });
  }

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

  clearLines() {
    var canvas = <HTMLCanvasElement>document.getElementById('tutorial');
    var ctx = canvas.getContext('2d');
    ctx.clearRect(0, 0, canvas.width, canvas.height);
  }

  destoryStartInterval() {
    if (this.startcounter) {
      clearInterval(this.startcounter);
      console.log("start interval destoryed!");
    }
  }

  destoryIntervel(username: string) {
    this.showbutton = true;
    this.wordFormControl.disable();
    if (this.intervelcounter) {
      clearInterval(this.intervelcounter);
      alert("player " + username + " won!!");
      //this.router.navigate(['home']);
    }
  }

  ngOnDestroy() {
    clearInterval(this.startcounter);
    clearInterval(this.intervelcounter);
    console.log("destorying intervals");
  }

}





