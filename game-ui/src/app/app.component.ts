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

  constructor(private playerService: PlayerService, private router: Router) {
  }

  logout () {
    this.playerService.playerLogout();
    this.router.navigate(['/login']);
  }
}
