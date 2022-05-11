import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Router } from '@angular/router';
import { Player } from 'src/models/player.model';
import { PlayerService } from 'src/services/player.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  //local variables used for the form inputs
  usernameFormControl = new FormControl('', [Validators.required]);
  passwordFormControl = new FormControl('', [Validators.required]);

  logPlayer: Player = new Player();
  loggedIn: Boolean = false;

  constructor(private playerService: PlayerService, private router: Router) { }

  ngOnInit(): void {
    sessionStorage.removeItem("username");
  }

  //takes you to the register/sign up page
  signUp() {
    this.loggedIn = true;
    this.router.navigate(['/register']);
  }

  //check if username and password fields are not empty and valid before sending that information to
  //the backend to be authenticated
  authPlayer() {

    if (this.usernameFormControl.hasError("required")) {
      alert("please enter username");
    } else if (this.passwordFormControl.hasError('required')){
      alert("please enter password");
    } else {
      this.logPlayer.userName = this.usernameFormControl.value;
      this.logPlayer.password = this.passwordFormControl.value;
      this.playerService.playerLogIn(this.logPlayer).subscribe(data => {
        //console.log(data);
        //user authenticated successfully so they can access the home page
        if (data) {
          sessionStorage.setItem("username", this.logPlayer.userName);
          this.loggedIn = true;
          this.router.navigate(['/home']);
        } else {
          alert("enter a valid username and password");
        }
      });
    }

  }



}
