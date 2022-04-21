import { Component, OnInit } from '@angular/core';
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
  gameId: string;

  constructor(private gameService: GameService, private router: Router, private playerService: PlayerService) { }

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

  beginGame(id: string) {
    console.log(id);
    this.gameId = id;
    this.router.navigate(['/game']);
  }

  joinGame(id: string) {
    this.gameId = id;
  }

}
