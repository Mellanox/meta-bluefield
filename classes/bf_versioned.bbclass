# Inherit from this class to set the recipe's version
# to match whatever the bluefield release version is.

def get_bf_version(d):
    UNKNOWN_VER = "0.0.dev.99999"

    version = d.getVar("MLNX_BLUEFIELD_VERSION")

    if not version:
        return UNKNOWN_VER

    return version

def set_package_version(d):
    bfversion = get_bf_version(d)
    major, minor, patch, buildno = bfversion.split(".")

    version = ".".join([major, minor, patch])

    d.setVar("PV", version)
    d.setVar("PR", buildno)

    d.setVar("BF_VERSIONED_BLUEFIELD_VERSION", bfversion)

python () {
    set_package_version(d)
}
