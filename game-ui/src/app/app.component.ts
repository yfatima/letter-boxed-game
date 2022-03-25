import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import Chart from 'chart.js/auto';

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}
function drawBox() {
  var canvas = <HTMLCanvasElement>document.getElementById('tutorial');
  var ctx = canvas.getContext('2d');
  const myChart = new Chart(ctx, {
    type: 'scatter',
    data: {
      datasets: [{
        data: [{
          x: 0,
          y: 0.2
        }, {
          x: 0,
          y: 1.5
        }, {
          x: 0,
          y: 2.8
        }, {
          x: 1.0,
          y: 0
        }, {
          x: 1.5,
          y: 0
        }, {
          x: 0.5,
          y: 0
        }, {
          x: 1,
          y: 3
        }, {
          x: 1.5,
          y: 3
        }, {
          x: 2,
          y: 1.5
        }, {
          x: 2,
          y: 2.8
        },{
          x: 2,
          y: 0.2
        }, {
          x: 0.5,
          y: 3
        },],
        backgroundColor: '#3f51b5',
      }],
      
    },
    options: {
      plugins:{
        tooltip: {
          enabled: false
        },
        legend: {
         display: false
        },  
      },
      scales: {
        x: {
          display: false
        },
        y: {
          display: false
        }
      },
      elements: {
        point: {
          radius: 10
        },
      },
    }
  });
}

function drawOutterBox() {
  var canvas = <HTMLCanvasElement>document.getElementById('letteredbox');
  var ctx = canvas.getContext('2d');
  //left side
  ctx.fillText("Y", 10, 35); 
  ctx.fillText("U", 10, 125);
  ctx.fillText("L", 10, 80);
  //top
  ctx.fillText("H", 85, 15);
  ctx.fillText("K", 145, 15);
  ctx.fillText("U", 210, 15);
  //right side
  ctx.fillText("W", 285, 35); 
  ctx.fillText("C", 285, 80);
  ctx.fillText("A", 285, 125);
  //bottom
  ctx.fillText("B", 85, 145);
  ctx.fillText("E", 145, 145); 
  ctx.fillText("G", 210, 145);
}
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'Letter Boxed Game';

  emailFormControl = new FormControl('', [Validators.required, Validators.pattern(
    '^[A-Za-z]{5}$')]);

  matcher = new MyErrorStateMatcher();
  ngOnInit() {
    drawOutterBox();
    drawBox();
  }
}
