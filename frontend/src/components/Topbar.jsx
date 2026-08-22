function Topbar() {
    return (
        <header className="h-16 border-b border-gray-200 bg-white flex items-center justify-between px-8">

            <div>
                <h2 className="text-lg font-semibold text-gray-900">
                    Overview
                </h2>
                <p className="text-xs text-gray-500">
                    Reverse logistics operations
                </p>
            </div>

            <div className="flex items-center gap-4">

                <div className="text-right">
                    <p className="text-sm font-medium text-gray-800">
                        Operations
                    </p>
                    <p className="text-xs text-gray-500">
                        Admin
                    </p>
                </div>

                <div className="w-9 h-9 rounded-full bg-gray-200 flex items-center justify-center text-sm font-semibold">
                    OP
                </div>

            </div>

        </header>
    );
}

export default Topbar;