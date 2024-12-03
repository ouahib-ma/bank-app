import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogModule, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { Partner, Direction, ProcessedFlowType } from '../../models/partner.model';

@Component({
  selector: 'app-partner-dialog',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule
  ],
  templateUrl: './partner-dialog.component.html'
})
export class PartnerDialogComponent {
  form: FormGroup;
  directions = Object.values(Direction);
  flowTypes = Object.values(ProcessedFlowType);

  constructor(
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) public data: Partner,
    private dialogRef: MatDialogRef<PartnerDialogComponent>
  ) {
    this.form = this.fb.group({
      id: [data?.id],
      alias: [data?.alias || '', [Validators.required]],
      type: [data?.type || '', [Validators.required]],
      direction: [data?.direction || '', [Validators.required]],
      application: [data?.application || ''],
      processedFlowType: [data?.processedFlowType || '', [Validators.required]],
      description: [data?.description || '', [Validators.required]]
    });
  }

  onSubmit() {
    if (this.form.valid) {
      this.dialogRef.close(this.form.value);
    }
  }
}

