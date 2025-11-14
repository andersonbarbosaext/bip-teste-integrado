import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListarBeneficiosComponent } from './pages/listar-beneficios/listar-beneficios.component';
import { CriarBeneficioComponent } from './pages/criar-beneficio/criar-beneficio.component';
import { EditarBeneficioComponent } from './pages/editar-beneficio/editar-beneficio.component';
import { TransferirBeneficioComponent } from './pages/transferir-beneficio/transferir-beneficio.component';

const routes: Routes = [
  { path: '', component: ListarBeneficiosComponent },
  { path: 'criar', component: CriarBeneficioComponent },
  { path: 'editar/:id', component: EditarBeneficioComponent },
  { path: 'transferir', component: TransferirBeneficioComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BeneficiosRoutingModule { }
