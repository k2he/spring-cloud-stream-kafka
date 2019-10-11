import { ErrorHandler, Injectable, Injector } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { LoggingService } from './services/logging.service';
import { ErrorService } from './services/error.service';
import { NotificationService } from './services/notification.service';

@Injectable()
export class GlobalErrorHandler implements ErrorHandler {

  constructor(private injector: Injector) { }
  
  handleError(error: Error | HttpErrorResponse) {
    const errorService = this.injector.get(ErrorService);
    const loggerService = this.injector.get(LoggingService);
    const notifyService = this.injector.get(NotificationService);

    let message;
    let stackTrace;
    if (error instanceof HttpErrorResponse) {
      // Server error
      message = errorService.getServerErrorMessage(error);
      notifyService.showError(message);
    } else {
      // Client Error
      message = errorService.getClientErrorMessage(error);
      notifyService.showError(message);
    }
    // Log errors
    loggerService.logError(message, stackTrace);
    console.error(message);
  }
}