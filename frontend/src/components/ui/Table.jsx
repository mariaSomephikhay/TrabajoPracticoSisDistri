import React from "react";

export const Table = ({ 
  columns, 
  data, 
  actions = [], 
  emptyMessage = "No hay datos disponibles" //Mensaje por defecto si no se le pasa uno especifico
}) => {
  if (!data || data.length === 0) { 
    return <p className="text-center mt-5">{emptyMessage}</p>
  }

  return (
    <table className="table" align="center">
      <thead>
        <tr>
          {actions.length > 0 && <th>Acciones</th>}
          {columns.map((col) => (
            <th key={col.key}>{col.header}</th>
          ))}
        </tr>
      </thead>
      <tbody>
        {data.map((row, i) => (
          <tr key={row.id || i}>
            {actions.length > 0 && (
              <td>
                {actions.map((action, idx) => {
                  // Si hidden devuelve true, no se renderiza el boton especifico
                  if (action.hidden?.(row)) return null 
                  return (
                    <button
                      key={idx}
                      className="btn btn-white btn-sm me-1"
                      onClick={() => action.onClick(row)}
                    >
                      {action.icon ? (
                        <img src={action.icon} alt={action.label} width={20} height={20} />
                      ) : action.label}
                    </button>
                  );
                })}
              </td>
            )}
            {columns.map((col) => {
              const content = col.render ? col.render(row[col.key], row) : row[col.key];
              const clickable = !!col.onCellClick;

              return (
                <td
                  key={col.key}
                  style={clickable ? { cursor: "pointer", color: "#0d6efd", textDecoration: "underline" } : {}}
                  onClick={clickable ? () => col.onCellClick(row) : undefined}
                >
                  {content}
                </td>
              );
            })}
          </tr>
        ))}
      </tbody>
    </table>
  )
}
