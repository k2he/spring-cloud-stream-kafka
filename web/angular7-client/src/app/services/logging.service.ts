import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoggingService {

  logError(message: string, stack: string) {
    // TODO: Send errors to server
    console.log('LoggingService: ' + message);
  }
}
