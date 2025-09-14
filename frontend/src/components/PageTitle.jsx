import React from "react"
import { Title } from "./ui/Title.jsx"

export const PageTitle = ({ level = "h1", text = "Título por defecto", className = "" }) => {
  return (
    <div>
      <Title level={level} text={text} className={className} />
    </div>
  )
}
