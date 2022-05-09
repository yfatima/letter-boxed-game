import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Player } from 'src/models/player.model';


const httpOptions = {
  headers: new HttpHeaders()
}

httpOptions.headers.append('Access-Control-Allow-Origin', '*');
httpOptions.headers.append('Content-Type', 'application/json');
httpOptions.headers.append('X-Frame-Options', 'SAMEORIGIN');
httpOptions.headers.append('X-Content-Type-Options', 'nosniff');

@Injectable({
  providedIn: 'root'
})
export class PlayerService {

  private baseURL = 'http://localhost:8080/player';

  constructor(private http: HttpClient) { }

  isPlayerLoggedIn() {
    let player = sessionStorage.getItem("username");
    return !(player == null);
  }

  playerLogIn(player : Player) {
    return this.http.post(this.baseURL + "/login", player, httpOptions);
  }

  createPlayer(player : Player) {
    return this.http.post(this.baseURL + "/create", player, httpOptions);
  }

  playerLogout() {
    sessionStorage.removeItem("username");
  }

  getPlayerByUsername(username: string, hashvalue: string) {
    return this.http.get<Player>(this.baseURL + "/getplayer/" + username + "/" + hashvalue, httpOptions);
  }

  getLoggedInPlayer() {
    return this.http.get<Player>(this.baseURL + "/logged", httpOptions);
  }

  getPlayerScore(username: string,  hashvalue: string) {
    return this.http.get<number>(this.baseURL + "/getplayerscore/" + username + "/" + hashvalue, httpOptions);
  }

  getPlayers() {
    return this.http.get<Player[]>(this.baseURL + "/players", httpOptions);
  }
}
