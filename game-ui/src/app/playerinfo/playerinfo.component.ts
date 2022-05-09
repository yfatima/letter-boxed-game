import { Component, OnInit } from '@angular/core';
import { Player } from 'src/models/player.model';
import { PlayerService } from 'src/services/player.service';

@Component({
  selector: 'app-playerinfo',
  templateUrl: './playerinfo.component.html',
  styleUrls: ['./playerinfo.component.scss']
})
export class PlayerinfoComponent implements OnInit {

  player : Player[];
  displayedColumns: string[] = ['username', 'Wins', 'Loses'];

  constructor(private playerService: PlayerService) { }

  ngOnInit(): void {
    let username = sessionStorage.getItem("username");
    this.playerService.getPlayers().subscribe ( data => {
      this.player = data;
    });
  }

}
