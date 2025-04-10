{
  description = "CLI Wordle clone written in java";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs?ref=nixos-unstable";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, flake-utils }:
    flake-utils.lib.eachDefaultSystem
      (system:
        let
          pkgs = nixpkgs.legacyPackages.${system};
        in
        {
          devShells.default = pkgs.mkShell {
            buildInputs = with pkgs; [ jdk maven ];
            shellHook = ''
              alias check='./scripts/check'
              alias format='./scripts/format'
              alias runcli='./scripts/runcli'
              alias report='./scripts/report'
              alias runtests='./scripts/runtests'
            '';
          };
        }
      );
}
