import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { BeneficiosRoutingModule } from './beneficios-routing.module';
import { ListarBeneficiosComponent } from './pages/listar-beneficios/listar-beneficios.component';
import { CriarBeneficioComponent } from './pages/criar-beneficio/criar-beneficio.component';
import { EditarBeneficioComponent } from './pages/editar-beneficio/editar-beneficio.component';
import { TransferirBeneficioComponent } from './pages/transferir-beneficio/transferir-beneficio.component';

@NgModule({
  declarations: [
    ListarBeneficiosComponent,
    CriarBeneficioComponent,
    EditarBeneficioComponent,
    TransferirBeneficioComponent
  ],
  imports: [
    CommonModule,
    BeneficiosRoutingModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class BeneficiosModule { }