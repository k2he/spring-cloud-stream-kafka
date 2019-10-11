import { Injectable } from '@angular/core';
import { Subject } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SpinnerService {

  constructor() { }

  private _show = new Subject<boolean>();

  show() {
    this._show.next(true);
  }

  hide() {
    this._show.next(false);
  }

  get events$() {
    return this._show.asObservable();
  }

}
