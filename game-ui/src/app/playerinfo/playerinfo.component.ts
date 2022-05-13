import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Player } from 'src/models/player.model';
import { PlayerService } from 'src/services/player.service';

@Component({
  selector: 'app-playerinfo',
  templateUrl: './playerinfo.component.html',
  styleUrls: ['./playerinfo.component.scss']
})
export class PlayerinfoComponent implements OnInit {

  //local variables for creating the table 
  player: Player[];
  displayedColumns: string[] = ['username', 'Wins', 'Loses'];

  constructor(private playerService: PlayerService, private router: Router) { }

  ngOnInit(): void {

    if (!this.playerService.isPlayerLoggedIn()) {
      this.router.navigate(['/login']);
    } else {
      //gets the list of all the players and displaying username, wins, and losses only
      this.playerService.getPlayers().subscribe(data => {
        this.player = data;
      });
    }

  }

}
