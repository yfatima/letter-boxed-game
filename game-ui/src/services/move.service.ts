import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Game } from 'src/models/game.model';
import { Player } from 'src/models/player.model';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';

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
export class MoveService {

  private baseURL = 'http://localhost:8080/move';

  constructor(private http: HttpClient) { }

  createMove(player: Player, word: String, gameid: String) {
    return this.http.post<Game>(this.baseURL + "/create", { word, player, gameid }, httpOptions);
  }

  skipMove(gameid: String) {
    return this.http.post<Game>(this.baseURL + "/skipmove", gameid, httpOptions);
  }
}
