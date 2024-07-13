package stonic.stonicusuariosbe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    String nombres;
    String apellidos;
    String clave;
    String dni;
    String telefono;
    String correo;
    String nickname;
    LocalDate fechanacimiento;
}