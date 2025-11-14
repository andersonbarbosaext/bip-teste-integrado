import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BeneficioService } from '../../services/beneficio.service';
import { Beneficio } from '../../models/beneficio';

@Component({
  templateUrl: './criar-beneficio.component.html',
})
export class CriarBeneficioComponent {

  form = this.fb.group({
    nome: ['', Validators.required],
    valor: [0, Validators.required]
  });

  constructor(
    private fb: FormBuilder,
    private service: BeneficioService,
    private router: Router
  ) {}

  salvar() {
    if (this.form.invalid) return;

    const payload = this.form.value as Beneficio;

    this.service.create(payload)
      .subscribe(() => this.router.navigate(['/beneficios']));
  }
}