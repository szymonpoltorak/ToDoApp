import { Component, Input } from '@angular/core';
import { ControlValueAccessor, FormControl, NG_VALUE_ACCESSOR } from "@angular/forms";

@Component({
    selector: 'app-password-field',
    templateUrl: './password-field.component.html',
    styleUrls: ['./password-field.component.scss'],
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: PasswordFieldComponent,
            multi: true,
        }
    ]
})
export class PasswordFieldComponent implements ControlValueAccessor {
    @Input() passwordLabel: string = "";
    @Input() passwordControl!: FormControl;

    registerOnChange(onChange: any): void {
        this.onChange = onChange;
    }

    registerOnTouched(onTouched: any): void {
        this.onTouched = onTouched;
    }

    writeValue(obj: any): void {
    }

    private onChange: any = (): void => {
    };

    private onTouched: any = (): void => {
    };
}
