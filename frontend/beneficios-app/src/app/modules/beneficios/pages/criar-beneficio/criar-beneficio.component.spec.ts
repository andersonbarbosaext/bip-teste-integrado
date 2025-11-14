import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CriarBeneficioComponent } from './criar-beneficio.component';

describe('CriarBeneficioComponent', () => {
  let component: CriarBeneficioComponent;
  let fixture: ComponentFixture<CriarBeneficioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CriarBeneficioComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CriarBeneficioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
