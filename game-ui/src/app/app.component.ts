import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { PlayerService } from 'src/services/player.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Letter Boxed Game';
  playerStatus = false;

  constructor(private playerService: PlayerService, private router: Router) {
    this.playerStatus = this.playerService.isPlayerLoggedIn();
  }

  //takes the user to the login page
  login() {
    this.playerStatus = true;
    this.router.navigate(['/login']);
  }

  //logs out the user 
  logout () {
    this.playerService.playerLogout();
    this.playerStatus = this.playerService.isPlayerLoggedIn();
    this.router.navigate(['']);
  }

  //takes the user to the home page
  homepage() {
    if (this.playerService.isPlayerLoggedIn()) {
      this.router.navigate(['/home']);
    }
  }

  //takes the user to past games page
  pastgames() {
    if (this.playerService.isPlayerLoggedIn()) {
      this.router.navigate(['/pastgames']);
    }
  }

  //takes the user to players infor page
  showplayerinfo() {
    if (this.playerService.isPlayerLoggedIn()) {
      this.router.navigate(['/playerinfo']);
    }
  }
}
