import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarBeneficiosComponent } from './listar-beneficios.component';

describe('ListarBeneficiosComponent', () => {
  let component: ListarBeneficiosComponent;
  let fixture: ComponentFixture<ListarBeneficiosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListarBeneficiosComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListarBeneficiosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
