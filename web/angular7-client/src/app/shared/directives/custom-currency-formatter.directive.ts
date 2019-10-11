import { Directive, HostListener, ElementRef, AfterContentInit } from '@angular/core';
import { CustomCurrencyPipe } from "../pipes/custom-currency.pipe";

@Directive({
    selector: '[appCustomCurrencyFormatter]'
})
export class CustomCurrencyFormatterDirective implements AfterContentInit {
    constructor(private elementRef: ElementRef, private currencyPipe: CustomCurrencyPipe) {
    }

    ngAfterContentInit() {
        this.elementRef.nativeElement.value = this.currencyPipe.transform(this.elementRef.nativeElement.value);
    }

    @HostListener("focus", ["$event.target.value"])
    onFocus(value) {
        this.elementRef.nativeElement.value = this.currencyPipe.parse(value);
    }

    @HostListener("blur", ["$event.target.value"])
    onBlur(value) {
        this.elementRef.nativeElement.value = this.currencyPipe.transform(value);
    }
}
