import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Game } from 'src/models/game.model';
import { Player } from 'src/models/player.model';

const httpOptions = {
  headers: new HttpHeaders()
}

httpOptions.headers.append('Access-Control-Allow-Origin', '*');
httpOptions.headers.append('Content-Type', 'application/json');

@Injectable({
  providedIn: 'root'
})
export class MoveService {

  private baseURL = 'http://localhost:8080/move';

  constructor(private http: HttpClient) { }

  createMove(player: Player, word: String) {
    return this.http.post<Game>(this.baseURL + "/create", {word, player}, httpOptions);
  }
}
