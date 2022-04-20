import { Player } from "./player.model";

export class Game {
    id: String;
    p1Id: String;
    p2Id: String;
    gameStatus: String;
    letters: String[];
    wordsUsed: String[];
    winScore: number;
}