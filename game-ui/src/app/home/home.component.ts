import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Game } from 'src/models/game.model';
import { GameService } from 'src/services/game.service';
import { PlayerService } from 'src/services/player.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  //local variables 
  appname = 'Letter Boxed Game';
  startedGame: Game;
  gameId: string = "newgame";
  //game id must match this RE pattern
  gameidFormControl = new FormControl('', [Validators.pattern('^[0-9]+$')]);

  constructor(private gameService: GameService, private router: Router, private playerService: PlayerService) { }

  ngOnInit(): void {
  }

  //send game type to game component and go there
  //called when the user clicks new game
  beginGame() {
    this.router.navigate(['/game/type', this.gameId]);
  }

  //validates the game id and then lets the user join the game if backend check is good
  joinGame() {
    console.log(this.gameidFormControl.hasError('pattern'));
    this.gameService.getGameStatus(this.gameidFormControl.value).subscribe(data => {
      if (data.gameStatus == "active") {
        if (!this.gameidFormControl.hasError('pattern')) {
          this.gameId = this.gameidFormControl.value;
          this.router.navigate(['/game/type', this.gameId]);
        } else {
          alert("please enter a valid game id");
        }

      } else {
        alert("game not started yet");
      }
    });
  }

}
