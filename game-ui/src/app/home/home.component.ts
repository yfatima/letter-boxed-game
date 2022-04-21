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

  appname = 'Letter Boxed Game';
  typesOfGames: Game[];
  gameId: string = "newgame";

  constructor(private gameService: GameService, private router: Router, private playerService: PlayerService) { }

  gameidFormControl = new FormControl('', [Validators.pattern('^[0-9]+$')]);

  ngOnInit(): void {

    if (this.playerService.isPlayerLoggedIn()) {
      this.gameService.getGames().subscribe( data => {
        console.log(data);
        this.typesOfGames = data;
      });
    } else {
      this.router.navigate(['/login']);
    }
  }

  beginGame() {
    this.router.navigate(['/game', this.gameId]);
  }

  joinGame() {
    console.log(this.gameidFormControl.hasError('pattern'));
    if (!this.gameidFormControl.hasError('pattern')) {
      this.gameId = this.gameidFormControl.value;
      this.router.navigate(['/game', this.gameId]);
    }
    
  }

}
