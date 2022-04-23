import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { ActivatedRoute, Router } from '@angular/router';
import Chart from 'chart.js/auto';
import { interval, Subscription, timer } from 'rxjs';
import { Game } from 'src/models/game.model';
import { Player } from 'src/models/player.model';
import { GameService } from 'src/services/game.service';
import { PlayerService } from 'src/services/player.service';
import { HomeComponent } from '../home/home.component';

function drawBox() {
  var canvas = <HTMLCanvasElement>document.getElementById('tutorial');
  var ctx = canvas.getContext('2d');
  const myChart = new Chart(ctx, {
    type: 'scatter',
    data: {
      datasets: [{
        data: [{
          x: 0,
          y: 0.2
        }, {
          x: 0,
          y: 1.5
        }, {
          x: 0,
          y: 2.8
        }, {
          x: 1.0,
          y: 0
        }, {
          x: 1.5,
          y: 0
        }, {
          x: 0.5,
          y: 0
        }, {
          x: 1,
          y: 3
        }, {
          x: 1.5,
          y: 3
        }, {
          x: 2,
          y: 1.5
        }, {
          x: 2,
          y: 2.8
        }, {
          x: 2,
          y: 0.2
        }, {
          x: 0.5,
          y: 3
        },],
        backgroundColor: '#3f51b5',
      }],

    },
    options: {
      plugins: {
        tooltip: {
          enabled: false
        },
        legend: {
          display: false
        },
      },
      scales: {
        x: {
          display: false
        },
        y: {
          display: false
        }
      },
      elements: {
        point: {
          radius: 10
        },
      },
    }
  });
}

function drawOutterBox(data: string[]) {
  var canvas = <HTMLCanvasElement>document.getElementById('letteredbox');
  var ctx = canvas.getContext('2d');
  //left side
  ctx.fillText(data[0], 10, 35);
  ctx.fillText(data[1], 10, 125);
  ctx.fillText(data[2], 10, 80);
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

  subscription: Subscription;

  constructor(

    private playerService: PlayerService,
    private gameService: GameService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    let action = this.route.snapshot.paramMap.get('action');
    let username = sessionStorage.getItem("username");
    //get user by username
    if (username != null && action == 'newgame') {
      console.log("in new game");
      this.playerService.getPlayerByUsername(username).subscribe(data => {
        console.log(data);
        this.player = data;
        //make a game 
        this.gameService.createGame(this.player).subscribe(data => {
          this.game = data;
          //console.log(this.game);
          drawOutterBox(this.game.letters);
          drawBox();
        });
      });

      this.subscription = timer(10000).subscribe ( val => {
        this.gameService.getGameStatus(this.game.id).subscribe( (data) => {
          // console.log(data);
          if (data.gameStatus == "active") {
            this.game = data;
            this.destoryTimer();
          }
        });
      });

    } else if (username != null && action != "newgame") {

      this.playerService.getPlayerByUsername(username).subscribe(data => {
        console.log(data);
        this.player = data;
        //make a game 
        this.gameService.joinGame(action, this.player).subscribe(data => {
          this.game = data;
          if (this.game.id != null) {
            drawOutterBox(this.game.letters);
            drawBox();
          } else {
            this.router.navigate(['/home']);
          }
        });
      });

      this.gameService.updateGameStatus(this.player, this.game.id).subscribe (data => {});
      
    }
    this.gameService.getGameStatus(this.game.id).subscribe (data => {
      console.log(data);
      this.game = data;
    });
  }

  checkWord() {
    console.log(this.wordFormControl.value);
    //make move
  }

  destoryTimer() {
    this.subscription.unsubscribe();
  }
 
  startGame() {
    
  }
}



