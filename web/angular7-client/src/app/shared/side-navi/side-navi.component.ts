import { Component, OnInit, Input } from '@angular/core';
import { SideNavItem } from './side-navi.types';

@Component({
  selector: 'app-side-navi',
  templateUrl: './side-navi.component.html',
  styleUrls: ['./side-navi.component.css']
})
export class SideNaviComponent implements OnInit {

  @Input('naviItemList') naviItemList: SideNavItem[];

  constructor() { }

  ngOnInit() {
  }

}
