import { Component, Input } from '@angular/core';
import { ControlValueAccessor, FormControl, NG_VALUE_ACCESSOR } from "@angular/forms";

@Component({
    selector: 'app-name-field',
    templateUrl: './name-field.component.html',
    styleUrls: ['./name-field.component.scss'],
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: NameFieldComponent,
            multi: true,
        }
    ]
})
export class NameFieldComponent implements ControlValueAccessor {
    @Input() nameLabel: string = "";
    @Input() nameControl !: FormControl;

    constructor() {
    }

    registerOnChange(onChange: any): void {
        this.onChange = onChange;
    }

    registerOnTouched(onTouched: any): void {
        this.onTouched = onTouched;
    }

    writeValue(obj: any): void {
    }

    private onChange = () => {
    };

    private onTouched = () => {
    };
}
