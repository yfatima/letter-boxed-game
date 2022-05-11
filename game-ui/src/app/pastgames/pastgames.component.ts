import { Component, OnInit } from '@angular/core';
import { Game } from 'src/models/game.model';
import { GameService } from 'src/services/game.service';

@Component({
  selector: 'app-pastgames',
  templateUrl: './pastgames.component.html',
  styleUrls: ['./pastgames.component.scss']
})
export class PastgamesComponent implements OnInit {

  //local variables used to create the table
  games: Game[];
  displayedColumns: string[] = ['id', 'player 1', 'player 2', 'letters', 'words used', 'winscore', 'winner'];

  constructor(private gameService: GameService) { }

  ngOnInit(): void {
    //gets the list of all past games from the backend 
    this.gameService.getGames().subscribe ( data => {
        this.games = data;
    });
  }


}
