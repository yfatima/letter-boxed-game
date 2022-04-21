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


  usernameFormControl = new FormControl('', [Validators.required]);
  emailFormControl = new FormControl('', [Validators.required, Validators.email]);
  passwordFormControl = new FormControl('', [Validators.required]);

  p: Player = new Player();

  constructor(private playerService: PlayerService, private router: Router) { }

  ngOnInit(): void {
  }

  createPlayer() {
    //console.log(this.usernameFormControl.value);
    this.p.userName = this.usernameFormControl.value;
    this.p.email = this.emailFormControl.value;
    this.p.password = this.passwordFormControl.value;
    this.p.wordList = [];
    this.p.score = 0;
    this.playerService.createPlayer(this.p).subscribe(data => {
      console.log(data);
      this.router.navigate(['/login']);
    });


  }

}
