import { Component } from '@angular/core';

import { MyserviceService} from '../Services/myservice.service';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
   public name: any = '';

    constructor(private MyserviceService: MyserviceService) {


    }

   onSubmit() {

    this.MyserviceService.getdata(this.name).then((data: any) => {
    console.log(data);

    });

   }


}
