SUMMARY = "Devicetree"
DESCRIPTION = "VEC6200 device tree"
SECTION = "bsp"

inherit devicetree

COMPATIBLE_MACHINE = "(vec6200|vec6200-factory)"

SRCREV ?= "b8e1807e5fb685b4ef3d4e1e71ee705b1eb9ff86"
SRC_URI = "git://git@github.com/data-respons-solutions/uboot-vec6200.git;protocol=ssh;branch=master;"

S = "${WORKDIR}/git/arch/arm/dts"

do_install() {
    for DTB_FILE in `ls *.dtb *.dtbo`; do
        install -Dm 0644 ${B}/${DTB_FILE} ${D}/boot/${DTB_FILE}
    done
}

devicetree_do_deploy() {
    for DTB_FILE in `ls *.dtb *.dtbo`; do
        install -Dm 0644 ${B}/${DTB_FILE} ${DEPLOYDIR}/${DTB_FILE}
    done
}

FILES_${PN} = "/boot/*.dtb /boot/devicetree/*.dtbo"