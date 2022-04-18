import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Game } from 'src/models/game.model';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  private baseURL = 'http://localhost:8080/game';

  constructor(private http: HttpClient) {}

  getGames() {
    return this.http.get<Game[]>(this.baseURL + "/list");
  }

  getGame(id: String) {
    return this.http.get<Game>(this.baseURL + "/" + id);
  }

  createGame(gameNumber: number) {
    return this.http.post(this.baseURL + "/create", gameNumber);
  }

  joinGame(gameNumber: number) {
    return this.http.post(this.baseURL + "/join", gameNumber);
  }

  getGameStatus(id: String) {
    return this.http.post(this.baseURL + "/gamestatus", id);
  }

}
