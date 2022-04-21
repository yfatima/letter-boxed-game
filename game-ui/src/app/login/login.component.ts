import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Player } from 'src/models/player.model';
import { PlayerService } from 'src/services/player.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  usernameFormControl = new FormControl('', [Validators.required]);
  passwordFormControl = new FormControl('', [Validators.required]);

  logPlayer: Player = new Player();
  loggedIn : Boolean = false;

  constructor(private playerService: PlayerService, private router: Router) { }

  ngOnInit(): void {
  }

  signUp() {
    this.loggedIn = true;
    this.router.navigate(['/register']);
  }

  authPlayer() {
    this.logPlayer.userName = this.usernameFormControl.value;
    this.logPlayer.password = this.passwordFormControl.value;
    this.playerService.playerLogIn(this.logPlayer).subscribe(data => {
      console.log(data);
      if (data) {
        sessionStorage.setItem("username", this.logPlayer.userName);
        this.loggedIn = true;
        this.router.navigate(['/home']);
      }
      
    });
  }



}
