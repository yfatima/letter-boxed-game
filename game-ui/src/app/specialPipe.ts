import { Pipe, PipeTransform } from '@angular/core';
@Pipe({
  name: 'specialPipe',
})
export class specialPipe implements PipeTransform {
  //this function escapes any special characters in the string from the backend data
  transform(value: string): string {
    let newVal = value.replace(/[^\w\s]/gi, '').toLocaleLowerCase();
    return newVal;
  }
}