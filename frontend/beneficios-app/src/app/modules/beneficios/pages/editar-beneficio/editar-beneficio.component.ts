import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { BeneficioService } from '../../services/beneficio.service';
import { Beneficio } from '../../models/beneficio';

@Component({
  templateUrl: './editar-beneficio.component.html',
})
export class EditarBeneficioComponent implements OnInit {

  id!: number;

  form = this.fb.group({
    nome: ['', Validators.required],
    valor: [0, Validators.required]
  });

  constructor(
    private fb: FormBuilder,
    private service: BeneficioService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.service.findById(this.id).subscribe(b => this.form.patchValue(b));
  }

  salvar() {
    if (this.form.invalid) return;
    const payload = this.form.value as Beneficio;
    this.service.update(this.id, payload)
      .subscribe(() => this.router.navigate(['/beneficios']));
  }
}