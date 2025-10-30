import React, { useState } from "react";
import { Table } from "../../../components/ui/Table.jsx";
import { Loading } from "../../../components/ui/Loading.jsx";
import SoapService from "../../../services/SoapService.js";
import searchIcon from "../../../../public/icons/search.png";

export const SoapConsult = () => {
  const [idsInput, setIdsInput] = useState("");
  const [ongs, setOngs] = useState([]);
  const [presidentes, setPresidentes] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // --- Manejo del input ---
  const handleInputChange = (e) => {
    setIdsInput(e.target.value);
  };

  // --- Manejo de la consulta ---
  const handleSearch = async () => {
    setError(null);
    setOngs([]);
    setPresidentes([]);

    // Validar que haya IDs
    if (!idsInput.trim()) {
      setError("Debe ingresar al menos un ID separado por comas.");
      return;
    }

    try {
      setLoading(true);

      // Transformar "6,5,8" → [6,5,8]
      const idsArray = idsInput
        .split(",")
        .map((id) => parseInt(id.trim()))
        .filter((id) => !isNaN(id));

      if (idsArray.length === 0) {
        setError("Ingrese IDs válidos (números separados por coma).");
        setLoading(false);
        return;
      }

      // Llamar a ambos endpoints
      const [ongsResp, presResp] = await Promise.all([
        SoapService.obtenerDatosONGPorId({ ids: idsArray }),
        SoapService.obtenerPresidentesPrId({ ids: idsArray }),
      ]);

      // Manejo de respuesta (por si viene con .data o directamente)
      setOngs(ongsResp.data || ongsResp);
      setPresidentes(presResp.data || presResp);
    } catch (err) {
      console.error(err);
      setError("Error al consultar datos. Intente nuevamente.");
      setOngs([]);
      setPresidentes([]);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <Loading />;

  return (
    <div className="col-10 mx-auto mt-5">
      <h2 className="text-center mb-4 text-primary fw-bold">
        Consulta de Presidentes y ONGs (SOAP)
      </h2>

        {/* FORMULARIO DE IDS */}
        <div className="card shadow-sm p-4 mb-4">
        <h5 className="mb-3">Buscar por IDs de organización</h5>

        <div className="input-group">
            <input
            type="text"
            className="form-control"
            placeholder="Ejemplo: 6,5,8,10"
            value={idsInput}
            onChange={handleInputChange}
            />
            <button
            className="btn btn-primary d-flex align-items-center justify-content-center px-3"
            type="button"
            onClick={handleSearch}
            style={{ minWidth: "50px" }}
            >
            <img
                src={searchIcon}
                alt="Buscar"
                width="18"
                height="18"
                style={{ filter: "invert(1)" }} // blanco sobre fondo azul
            />
            </button>
        </div>

        {error && (
            <div className="alert alert-danger mt-3 mb-0 py-2">{error}</div>
        )}
        </div>

      {/* TABLA DE ONGs */}
      {error ? null : (
        <div className="card shadow-sm p-4 mb-5">
          <h5 className="text-secondary mb-3">Organizaciones Encontradas</h5>
          {ongs.length > 0 ? (
            <Table
              columns={[
                { key: "id", header: "ID" },
                { key: "name", header: "Nombre" },
                { key: "address", header: "Dirección" },
                { key: "phone", header: "Teléfono" },
              ]}
              data={ongs}
              emptyMessage="No se encontraron ONGs."
            />
          ) : (
            <p className="text-muted fst-italic">Sin resultados aún.</p>
          )}
        </div>
      )}

      {/* TABLA DE PRESIDENTES */}
      {error ? null : (
        <div className="card shadow-sm p-4 mb-5">
          <h5 className="text-secondary mb-3">Presidentes Encontrados</h5>
          {presidentes.length > 0 ? (
            <Table
              columns={[
                { key: "id", header: "ID" },
                { key: "name", header: "Nombre" },
                { key: "address", header: "Dirección" },
                { key: "phone", header: "Teléfono" },
                { key: "organization_id", header: "ID Organización" },
              ]}
              data={presidentes}
              emptyMessage="No se encontraron presidentes."
            />
          ) : (
            <p className="text-muted fst-italic">Sin resultados aún.</p>
          )}
        </div>
      )}
    </div>
  );
};