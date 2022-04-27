import { Pipe, PipeTransform } from '@angular/core';
@Pipe({
  name: 'specialPipe',
})
export class specialPipe implements PipeTransform {
  transform(value: string): string {
    let newVal = value.replace(/[^\w\s]/gi, '').toLocaleLowerCase();
    return newVal;
  }
  // titleCase(str) {
  //   var splitStr = str.toLowerCase().split(' ');
  //   for (var i = 0; i < splitStr.length; i++) {
  //     splitStr[i] =
  //       splitStr[i].charAt(0).toUpperCase() + splitStr[i].substring(1);
  //   }
  //   return splitStr.join(' ');
  // }
}