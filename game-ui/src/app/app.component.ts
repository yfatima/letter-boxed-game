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

  login() {
    this.playerStatus = true;
    this.router.navigate(['/login']);
  }

  logout () {
    this.playerService.playerLogout();
    this.playerStatus = this.playerService.isPlayerLoggedIn();
    this.router.navigate(['']);
  }

  homepage() {
    if (this.playerService.isPlayerLoggedIn()) {
      this.router.navigate(['/home']);
    }
    
  }
}
