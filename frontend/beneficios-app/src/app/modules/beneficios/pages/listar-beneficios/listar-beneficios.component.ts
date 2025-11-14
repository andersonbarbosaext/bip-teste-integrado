import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BeneficioService } from '../../services/beneficio.service';
import { Beneficio } from '../../models/beneficio';

@Component({
  templateUrl: './listar-beneficios.component.html',
  styleUrls: ['./listar-beneficios.component.scss']
})
export class ListarBeneficiosComponent implements OnInit {

  beneficios: Beneficio[] = [];

  constructor(
    private service: BeneficioService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.load();
  }

  load() {
    this.service.findAll().subscribe(res => this.beneficios = res);
  }

  editar(id: number) {
    this.router.navigate(['/beneficios/editar', id]);
  }

  delete(id: number) {
    if (confirm('Deseja excluir?')) {
      this.service.delete(id).subscribe(() => this.load());
    }
  }
}