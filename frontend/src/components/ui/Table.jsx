// src/components/ui/Table.jsx
import React from "react";

export const Table = ({ columns, data, actions = [], emptyMessage }) => {
  return (
    <div style={{ paddingBottom: "10px" }}>
      <table style={styles.table}>
        <thead>
          <tr>
            {columns.map((col) => (
              <th key={col.key} style={styles.th}>
                {col.header}
              </th>
            ))}
            {actions.length > 0 && <th style={styles.th}>Acciones</th>}
          </tr>
        </thead>
        <tbody>
          {data.length === 0 ? (
            <tr>
              <td colSpan={columns.length + 1} style={styles.emptyCell}>
                {emptyMessage || "Sin datos"}
              </td>
            </tr>
          ) : (
            data.map((row, rowIndex) => (
              <tr key={rowIndex} style={rowIndex % 2 === 0 ? styles.trEven : styles.trOdd}>
                {columns.map((col) => (
                  <td key={col.key} style={styles.td}>
                    {col.render ? col.render(row[col.key], row) : row[col.key]}
                  </td>
                ))}
                {actions.length > 0 && (
                  <td style={styles.td}>
                    <div style={styles.actionContainer}>
                      {actions
                        .filter((action) => {
                          if (typeof action.show !== "function") return true;
                          return action.show(row);
                        })
                        .map((action, index) => (
                          <button
                            key={index}
                            onClick={() => action.onClick(row)}
                            style={styles.actionButton}
                          >
                            {action.icon && (
                              <img
                                src={action.icon}
                                alt={action.label}
                                style={{ width: "16px", height: "16px", marginRight: "6px" }}
                              />
                            )}
                            {action.label}
                          </button>
                        ))}
                    </div>
                  </td>
                )}
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
};

const styles = {
  table: {
    width: "100%",
    borderCollapse: "separate",
    borderSpacing: 0,
    fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif",
    fontSize: "14px",
    backgroundColor: "#fff",
    borderRadius: "8px",
    overflow: "hidden",
    boxShadow: "0 2px 8px rgba(0, 0, 0, 0.05)",
  },
  th: {
    backgroundColor: "#f5f6fa",
    color: "#2f3640",
    padding: "12px 16px",
    textAlign: "left",
    fontWeight: 600,
    borderBottom: "1px solid #e1e1e1",
  },
  td: {
    padding: "12px 16px",
    borderBottom: "1px solid #f0f0f0",
    verticalAlign: "middle",
    whiteSpace: "normal",
  },
  trEven: {
    backgroundColor: "#ffffff",
  },
  trOdd: {
    backgroundColor: "#f9f9f9",
  },
  emptyCell: {
    textAlign: "center",
    padding: "40px",
    color: "#888",
    fontStyle: "italic",
  },
   actionButton: {
    backgroundColor: "#0984e3",
    border: "none",
    color: "white",
    padding: "6px 12px",
    borderRadius: "4px",
    cursor: "pointer",
    fontSize: "13px",
    display: "inline-flex",
    alignItems: "center",
    transition: "background-color 0.2s ease",
    minWidth: "80px", 
    justifyContent: "center",
  },
  actionContainer: {
    display: "flex",
    flexWrap: "wrap", 
    gap: "8px",
    alignItems: "center",
  },
};
