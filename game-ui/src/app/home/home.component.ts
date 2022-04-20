import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Game } from 'src/models/game.model';
import { GameService } from 'src/services/game.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  appname = 'Letter Boxed Game';
  typesOfGames: Game[];
  gameTmp: Game;

  constructor(private gameService: GameService, private router: Router) { }

  ngOnInit(): void {
    this.gameService.getGames().subscribe( data => {
      console.log(data);
      this.typesOfGames = data;
    });
  }

  beginGame(game: Game) {
    console.log(game);
    this.gameService.createGame(game.id).subscribe( data => {
      console.log(data);
    });
    this.router.navigate(['/game']);
  }

}
