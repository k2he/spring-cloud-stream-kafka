import { Component, OnInit } from '@angular/core';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-back-end',
  templateUrl: './back-end.component.html',
  styleUrls: ['./back-end.component.scss']
})
export class BackEndComponent implements OnInit {

  apiPath: string;

  constructor() { }

  ngOnInit() {
    this.apiPath = environment.apiPath;
  }

}
