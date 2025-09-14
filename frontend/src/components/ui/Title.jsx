import React from "react"

export const Title = ({ level = "h2", text, className = "" }) => {
  const HeadingTag = level
  return <HeadingTag className={className}>{text}</HeadingTag>
}
