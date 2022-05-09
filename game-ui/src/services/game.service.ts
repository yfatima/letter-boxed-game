import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Game } from 'src/models/game.model';
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
export class GameService {

  private baseURL = 'http://localhost:8080/game';

  constructor(private http: HttpClient) {}

  getGames() {
    return this.http.get<Game[]>(this.baseURL + "/list", httpOptions);
  }

  getGame(id: String) {
    return this.http.get<Game>(this.baseURL + "/" + id, httpOptions);
  }

  createGame(player: Player) {
    return this.http.post<Game>(this.baseURL + "/create", player, httpOptions);
  }

  joinGame(gameId: String, player: Player) {
    return this.http.post<Game>(this.baseURL + "/join", {gameId, player}, httpOptions);
  }

  getGameStatus(id: String) {
    return this.http.post<Game>(this.baseURL + "/gamestatus", id, httpOptions);
  }

  updateGameStatus(player: Player, gameId: String) {
    return this.http.post(this.baseURL + "/updategamestatus", {gameId, player}, httpOptions);
  }

}
