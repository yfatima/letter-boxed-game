import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
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
export class GameService {

  private baseURL = 'http://localhost:8080/game';

  constructor(private http: HttpClient) {}

  getGames() {
    return this.http.get<Game[]>(this.baseURL + "/list", httpOptions);
  }

  getGame(id: String) {
    return this.http.get<Game>(this.baseURL + "/" + id);
  }

  createGame(player: Player) {
    return this.http.post<Game>(this.baseURL + "/create", player, httpOptions);
  }

  joinGame(gameId: String, player: Player) {
    return this.http.post<Game>(this.baseURL + "/join", {gameId, player}, httpOptions);
  }

  getGameStatus(id: String) {
    return this.http.post(this.baseURL + "/gamestatus", id);
  }

}
