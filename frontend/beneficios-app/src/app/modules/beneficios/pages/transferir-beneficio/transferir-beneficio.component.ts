import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { BeneficioService } from '../../services/beneficio.service';
import { TransferRequest } from '../../models/transfer-request';

@Component({
  templateUrl: './transferir-beneficio.component.html',
})
export class TransferirBeneficioComponent {

  form = this.fb.group({
    fromId: [null, Validators.required],
    toId: [null, Validators.required],
    valor: [null, Validators.required]
  });

  constructor(
    private fb: FormBuilder,
    private service: BeneficioService
  ) {}

  transferir() {
  if (this.form.invalid) return;

  this.service.transfer(this.form.value as TransferRequest)
    .subscribe(() => {
      alert('Transferência realizada!');
      this.form.reset();
    });
}
}