import { Component, OnInit } from '@angular/core';
import { Game } from 'src/models/game.model';
import { GameService } from 'src/services/game.service';

@Component({
  selector: 'app-pastgames',
  templateUrl: './pastgames.component.html',
  styleUrls: ['./pastgames.component.scss']
})
export class PastgamesComponent implements OnInit {

  constructor(private gameService: GameService) { }
  games: Game[];
  displayedColumns: string[] = ['id', 'player 1', 'player 2', 'letters', 'words used', 'winscore', 'winner'];

  ngOnInit(): void {
    this.gameService.getGames().subscribe ( data => {
        this.games = data;
    });
  }


}
