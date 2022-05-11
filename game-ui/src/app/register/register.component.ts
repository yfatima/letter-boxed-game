import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Player } from 'src/models/player.model';
import { PlayerService } from 'src/services/player.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {


  //local variables for form inputs and match password to a specfic RE
  usernameFormControl = new FormControl('', [Validators.required]);
  emailFormControl = new FormControl('', [Validators.required, Validators.email]);
  passwordFormControl = new FormControl('', [Validators.required, Validators.pattern(
    '(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&?]).{8,}')]);

  p: Player = new Player();

  constructor(private playerService: PlayerService, private router: Router) { }

  ngOnInit(): void {
  }

  //takes the user to the login page
  goLogin() {
    this.router.navigate(['/login']);
  }

  //creates a new user/player if all field are not empty and valid
  createPlayer() {
    if (this.usernameFormControl.hasError("required") || this.passwordFormControl.hasError('required') || this.emailFormControl.hasError("required")) {
      alert("all inputs are required");
    } else if (this.emailFormControl.hasError("email")) {
      alert("please enter a valid email");
    } else if (this.passwordFormControl.hasError("pattern")) {
      alert("please enter a valid password");
    } else {
      //settings values to the player object to sent to the backend
      this.p.userName = this.usernameFormControl.value;
      this.p.email = this.emailFormControl.value;
      this.p.password = this.passwordFormControl.value;
      this.p.score = 0;
      this.p.gamesWon = 0;
      this.p.gamesLost = 0;
      this.playerService.createPlayer(this.p).subscribe(data => {
        //console.log(data);
        if (data) {
          this.router.navigate(['/login']);
        } else {
          alert("new player not registered")
        }
        
      });
    }
  }

}
