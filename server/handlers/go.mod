module github.com/macewanCS/w24MacroHard/server/handlers

go 1.21.6

replace github.com/macewanCS/w24MacroHard/server/middleware => ../middleware

require github.com/macewanCS/w24MacroHard/server/middleware v0.0.0-00010101000000-000000000000

require github.com/lib/pq v1.10.9 // indirect
